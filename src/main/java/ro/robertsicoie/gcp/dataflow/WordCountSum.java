package ro.robertsicoie.gcp.dataflow;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.PipelineRunner;
import org.apache.beam.sdk.io.TextIO;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.Validation;
import org.apache.beam.sdk.transforms.Count;
import org.apache.beam.sdk.transforms.Filter;
import org.apache.beam.sdk.transforms.FlatMapElements;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.KV;
import org.apache.beam.sdk.values.TypeDescriptors;

import java.util.Arrays;

public class WordCountSum {

    public interface WordCountSumOptions extends PipelineOptions {

        @Description("Path of the output bucket.")
        @Validation.Required
        String getOutput();
        void setOutput(String value);
    }

    public static void main(String[] args) {
        WordCountSumOptions options = PipelineOptionsFactory.fromArgs(args).as(WordCountSumOptions.class);


        Pipeline pipeline = Pipeline.create(options);

        pipeline.apply(TextIO.read().from("gs://apache-beam-samples/shakespeare/hamlet.txt"))
                .apply(FlatMapElements.into(TypeDescriptors.strings())
                        .via((String word) -> Arrays.asList(word.split("[^\\p{L}]+"))))
                .apply(Filter.by((String word) -> word.equals("orchard")))
                .apply(Count.perElement())
                .apply(MapElements.into(TypeDescriptors.strings())
                        .via((KV<String, Long> wc) ->
                                wc.getKey() + ": " + wc.getValue()))
                .apply(TextIO.write().to(options.getOutput()));

        pipeline.run().waitUntilFinish();
    }
}
