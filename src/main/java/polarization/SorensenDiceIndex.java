package main.java.polarization;

import net.sf.tweety.arg.dung.semantics.Extension;
import net.sf.tweety.arg.dung.syntax.Argument;
import net.sf.tweety.commons.util.SetTools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class SorensenDiceIndex implements ExtensionBasedPolarizationMetric {
    @Override
    public double getPolarization(Collection<Extension> exts) {
        // get intersection
        Collection<Argument> intersection = new HashSet<>((exts.iterator().next()));
        for (Extension ext: exts) {
            intersection.retainAll(ext);
        }

        // get total number of elements
        int total = 0;
        for (Extension ext: exts) {
            total += ext.size();
        }

        return 1 - 2*(intersection.size() / (double)total);
    }

    @Override
    public double getPolarization(Extension ext1, Extension ext2) {
        Collection<Extension> exts = new HashSet<>();
        exts.add(ext1);
        exts.add(ext2);
        return this.getPolarization(exts);
    }
}
