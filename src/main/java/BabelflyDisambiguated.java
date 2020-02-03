import it.uniroma1.lcl.babelfy.commons.BabelfyConstraints;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.MCS;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.ScoredCandidates;
import it.uniroma1.lcl.babelfy.commons.BabelfyParameters.SemanticAnnotationResource;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation;
import it.uniroma1.lcl.babelfy.commons.annotation.SemanticAnnotation.Source;
import it.uniroma1.lcl.babelfy.commons.annotation.TokenOffsetFragment;
import it.uniroma1.lcl.babelfy.core.Babelfy;
import it.uniroma1.lcl.jlt.util.Language;

import java.util.List;
import java.util.Scanner;

public class
BabelflyDisambiguated {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String textToBeAnalized = scanner.nextLine();

        System.out.println("The matrix will be created based on: " + textToBeAnalized);

        String inputText = "BabelNet is both a multilingual encyclopedic dictionary and a semantic network";
        BabelfyConstraints constraints = new BabelfyConstraints();
        SemanticAnnotation a = new SemanticAnnotation(new TokenOffsetFragment(0, 0), "bn:03083790n",
                "http://dbpedia.org/resource/BabelNet", Source.OTHER);
        constraints.addAnnotatedFragments(a);
        BabelfyParameters bp = new BabelfyParameters();
        bp.setAnnotationResource(SemanticAnnotationResource.BN);
        bp.setMCS(MCS.ON_WITH_STOPWORDS);
        bp.setScoredCandidates(ScoredCandidates.ALL);
        Babelfy bfy = new Babelfy(bp);
        List<SemanticAnnotation> bfyAnnotations = bfy.babelfy(inputText, Language.EN, constraints);
        //bfyAnnotations is the result of Babelfy.babelfy() call
        for (SemanticAnnotation annotation : bfyAnnotations) {
            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1);
            System.out.println(frag + "\t" + annotation.getBabelSynsetID());
            System.out.println("\t" + annotation.getBabelNetURL());
            System.out.println("\t" + annotation.getDBpediaURL());
            System.out.println("\t" + annotation.getSource());
        }


    }
}
