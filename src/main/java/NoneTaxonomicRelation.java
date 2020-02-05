import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetRelation;
import it.uniroma1.lcl.babelnet.data.BabelPointer;
import it.uniroma1.lcl.jlt.util.Language;

import java.util.ArrayList;

public class NoneTaxonomicRelation {
    ArrayList<String> synsetList;

    public NoneTaxonomicRelation(ArrayList<String> synsetList) {
        this.synsetList = synsetList;
    }

    public void analyseNoneTaxonomic() {
        BabelNet bn = BabelNet.getInstance();
        BabelSynset tmpby;
        System.out.println("You will get Meronimos de: " + synsetList.size() + " synsets");
        for (String synset : synsetList) {
            BabelSynset by = bn.getSynset(new BabelSynsetID(synset));
            System.out.println("Synset=" + synset + " "
                    + by.getMainSense(Language.EN).get().getFullLemma());

            for (BabelSynsetRelation edge : by.getOutgoingEdges(BabelPointer.ANY_HYPERNYM)) {
                tmpby = bn.getSynset(new BabelSynsetID(edge.getBabelSynsetIDTarget().toString()));
                System.out.println(by.getID()
                        + "\t" + by.getMainSense(Language.EN).get().getFullLemma() + " - "
                        + "Puntero: " + edge.getPointer() + " - "
                        + edge.getBabelSynsetIDTarget() + " "
                        + tmpby.getMainSense(Language.EN).get().getFullLemma());

                BabelNetUtils.testGraph(edge.getBabelSynsetIDTarget());
            }
        }
    }

}
