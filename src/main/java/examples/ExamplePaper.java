package main.java.examples;

import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.arg.social.reasoner.IssReasoner;
import net.sf.tweety.arg.social.semantics.SimpleProductSemantics;
import net.sf.tweety.arg.social.syntax.SocialAbstractArgumentationFramework;

public class ExamplePaper {
    public static void main(String[] args) {
        SocialAbstractArgumentationFramework saaf = new SocialAbstractArgumentationFramework();
        saaf.add(new Argument("a"));
        saaf.add(new Argument("b"));
        saaf.add(new Argument("c"));
        saaf.add(new Argument("d"));
        saaf.add(new Argument("e"));
        saaf.addAttack(new Argument("a"), new Argument("b"));
        saaf.addAttack(new Argument("b"), new Argument("a"));
        saaf.addAttack(new Argument("c"), new Argument("b"));
        saaf.addAttack(new Argument("d"), new Argument("c"));
        saaf.addAttack(new Argument("e"), new Argument("c"));
        saaf.addAttack(new Argument("e"), new Argument("d"));
        saaf.addAttack(new Argument("d"), new Argument("e"));

        saaf.voteUp(new Argument("a"), 20);
        saaf.voteDown(new Argument("a"), 20);
        saaf.voteUp(new Argument("b"), 20);
        saaf.voteDown(new Argument("b"), 20);
        saaf.voteUp(new Argument("c"), 60);
        saaf.voteDown(new Argument("c"), 10);
        saaf.voteUp(new Argument("d"), 10);
        saaf.voteDown(new Argument("d"), 40);
        saaf.voteUp(new Argument("e"), 40);
        saaf.voteDown(new Argument("e"), 10);

        System.out.println("Model for Framework: ");
        IssReasoner reasoner = new IssReasoner(new SimpleProductSemantics(0.01, 0.001), 0.001);
        System.out.println(reasoner.getModel(saaf));
    }
}
