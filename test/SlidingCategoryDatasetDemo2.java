import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.data.category.*;

public class SlidingCategoryDatasetDemo2 extends ApplicationFrame {

    public SlidingCategoryDatasetDemo2(final String title) {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(createDemoPanel());
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> {
            final SlidingCategoryDatasetDemo2 demo = new SlidingCategoryDatasetDemo2("JFreeChart: SlidingCategoryDatasetDemo2.java");
            demo.pack();
            demo.setVisible(true);
        });
    }

    public static JPanel createDemoPanel() {
        CategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(400, 400));

        JScrollBar scroller = new JScrollBar(0, 0, 10, 0, 50);
        scroller.addAdjustmentListener(e -> {
            int firstCategoryIndex = scroller.getValue();
            ((SlidingCategoryDataset) dataset).setFirstCategoryIndex(firstCategoryIndex);
        });

        JPanel scrollPanel = new JPanel(new BorderLayout());
        scrollPanel.add(chartPanel, BorderLayout.CENTER);
        scrollPanel.add(scroller, BorderLayout.SOUTH);

        return scrollPanel;
    }

    private static JFreeChart createChart(final CategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createBarChart(
                "SlidingCategoryDatasetDemo2",
                "Series",
                "Value",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );
        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);
        domainAxis.setLowerMargin(0.02);
        domainAxis.setUpperMargin(0.02);
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setRange(0.0, 100.0);
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        final GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.BLUE, 0.0f, 0.0f, new Color(0, 0, 64));
        renderer.setSeriesPaint(0, gp0);
        return chart;
    }
    
    private static CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < 50; ++i) {
            dataset.addValue(Math.random() * 100.0, "S1", "S" + i);
        }
        return new SlidingCategoryDataset(dataset, 0, 10);
    }
}
