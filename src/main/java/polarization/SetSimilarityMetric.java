package main.java.polarization;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.commons.util.SetTools;

import java.util.*;

/**
 * Simple Polarization Metric based on set similarity.
 * The polarization of two extensions is defined as: 1 - (|E1 n E2| / |E1 u E2|)
 */
public class SetSimilarityMetric implements ExtensionBasedPolarizationMetric {
    @Override
    public double getPolarization(Collection<Extension> exts) {
        // get intersection
        Collection<Argument> intersection = new HashSet<>((exts.iterator().next()));
        for (Extension ext: exts) {
            intersection.retainAll(ext);
        }

        // get union
        Set<Set<Argument>> sets = new HashSet<>();
        for (Extension ext: exts) {
            sets.add(new HashSet<>(ext));
        }
        Collection<Argument> union = new SetTools<Argument>().getUnion(sets);

        return 1 - (intersection.size() / (double)union.size());

    }

    @Override
    public double getPolarization(Extension ext1, Extension ext2) {
        Collection<Extension> exts = new HashSet<>();
        exts.add(ext1);
        exts.add(ext2);
        return this.getPolarization(exts);
    }
}
