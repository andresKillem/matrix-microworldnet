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

import java.util.ArrayList;
import java.util.List;

public class BabelflyDisambiguated {
    public static void main(String[] args) {
//        String textToBeAnalized = scanner.nextLine();
//        System.out.println("The matrix will be created based on: " + textToBeAnalized);
//        Scanner scanner = new Scanner(System.in);

        String inputText = "a set of practices that combines software development and information-technology operations which aims to shorten the systems development life cycle and provide continuous delivery with high software quality";
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
        List<SemanticAnnotation> bfyAnnotationsDiscriminated = new ArrayList<>();
        ArrayList<String> synsetList = new ArrayList<>();
        for (SemanticAnnotation annotation : bfyAnnotations) {
            //splitting the input text using the CharOffsetFragment start and end anchors
            String frag = inputText.substring(annotation.getCharOffsetFragment().getStart(),
                    annotation.getCharOffsetFragment().getEnd() + 1);
            if (annotation.getScore() >= 0.5) {
                bfyAnnotationsDiscriminated.add(annotation);
                System.out.println(frag + "\t" + annotation.getBabelSynsetID());
                System.out.println("\t" + annotation.getBabelNetURL());
                System.out.println("\t" + annotation.getDBpediaURL());
                System.out.println("\t" + annotation.getSource());
                System.out.println("\t" + annotation.getScore());
                synsetList.add(annotation.getBabelSynsetID());

            }
        }
        System.out.println("Total of synset:" + bfyAnnotations.size());
        System.out.println("Total of synsets to Classify:" + bfyAnnotationsDiscriminated.size());
        NoneTaxonomicRelation noneTaxonomicRelation = new NoneTaxonomicRelation(synsetList);
        noneTaxonomicRelation.analyseNoneTaxonomic();

    }
}
