package main.java.examples;

import main.java.polarization.ExtensionBasedPolarizationMetric;
import main.java.polarization.JaccardIndex;
import main.java.semantics.WeightedSemantics;
import main.java.syntax.PoliticalPolarizationFramework;
import net.sf.tweety.arg.dung.semantics.Semantics;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.arg.dung.syntax.DungTheory;
import net.sf.tweety.arg.social.semantics.SimpleProductSemantics;
import net.sf.tweety.arg.social.syntax.SocialAbstractArgumentationFramework;

public class ppExample1 {
    public static void main(String[] args) {
        // create theory
        Argument a = new Argument("a_1");
        Argument b = new Argument("a_2");
        Argument c = new Argument("a_3");
        Argument d = new Argument("a_4");
        Argument e = new Argument("a_5");

        DungTheory dt = new DungTheory();
        dt.add(a);
        dt.add(b);
        dt.add(c);
        dt.add(d);
        dt.add(e);

        //dt.addAttack(a, b);
        dt.addAttack(b, a);
        dt.addAttack(c, b);
        dt.addAttack(d, a);
        dt.addAttack(e, d);
        dt.addAttack(d, e);
        dt.addAttack(d, c);
        dt.addAttack(b, c);

        // create social frameworks
        SocialAbstractArgumentationFramework saaf1 = new SocialAbstractArgumentationFramework();
        SocialAbstractArgumentationFramework saaf2 = new SocialAbstractArgumentationFramework();
        saaf1.add(dt);
        saaf2.add(dt);

        // votes for social framework 1
        saaf1.voteUp(a, 20);
        saaf1.voteDown(a, 20);
        saaf1.voteUp(b, 40);
        saaf1.voteDown(b, 20);
        saaf1.voteUp(c, 20);
        saaf1.voteDown(c, 20);
        saaf1.voteUp(d, 25);
        saaf1.voteDown(d, 40);
        saaf1.voteUp(e, 50);
        saaf1.voteDown(e, 40);

        saaf2.voteUp(a, 20);
        saaf2.voteDown(a, 20);
        saaf2.voteUp(b, 30);
        saaf2.voteDown(b, 20);
        saaf2.voteUp(c, 70);
        saaf2.voteDown(c, 10);
        saaf2.voteUp(d, 25);
        saaf2.voteDown(d, 40);
        saaf2.voteUp(e, 40);
        saaf2.voteDown(e, 10);


        // initialize political polarization framework
        SimpleProductSemantics socialSemantics = new SimpleProductSemantics(0.01, 0.001);
        Semantics semantics = Semantics.PREFERRED_SEMANTICS;
        WeightedSemantics weightedSemantics = WeightedSemantics.BEST4;
        ExtensionBasedPolarizationMetric polarizationMetric = new JaccardIndex();
        PoliticalPolarizationFramework ppf = new PoliticalPolarizationFramework(socialSemantics, semantics, weightedSemantics, polarizationMetric);

        System.out.println(ppf.computePolarization(saaf1, saaf2));

    }
}
