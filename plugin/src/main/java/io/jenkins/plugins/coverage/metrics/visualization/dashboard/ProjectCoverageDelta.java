package io.jenkins.plugins.coverage.metrics.visualization.dashboard;

import java.util.Locale;
import java.util.Optional;

import edu.hm.hafner.metric.Metric;

import io.jenkins.plugins.coverage.metrics.CoverageBuildAction;
import io.jenkins.plugins.coverage.metrics.CoveragePercentage;
import io.jenkins.plugins.coverage.metrics.Messages;
import io.jenkins.plugins.coverage.metrics.visualization.colorization.ColorProvider.DisplayColors;
import io.jenkins.plugins.coverage.metrics.visualization.colorization.CoverageChangeTendency;

/**
 * Concrete implementation of {@link CoverageColumnType} which represents the project coverage delta.
 *
 * @author Florian Orendi
 */
public class ProjectCoverageDelta extends CoverageColumnType {

    /**
     * Creates a column type to be used for representing the project coverage delta.
     */
    public ProjectCoverageDelta() {
        super(Messages._Project_Coverage_Delta_Type());
    }

    @Override
    public Optional<CoveragePercentage> getCoverage(final CoverageBuildAction action, final Metric metric) {
        if (action.hasDelta(metric)) {
            return Optional.of(CoveragePercentage.valueOf(action.getDelta().get(metric)));
        }
        return Optional.empty();
    }

    @Override
    public DisplayColors getDisplayColors(final CoveragePercentage coverage) {
        return CoverageChangeTendency.getDisplayColorsForTendency(coverage.getDoubleValue(), getColorProvider());
    }

    @Override
    public String formatCoverage(final CoveragePercentage coverage, final Locale locale) {
        return coverage.formatDeltaPercentage(locale);
    }
}
