package com.vulpes.ai.utils;

import com.vulpes.ai.Resource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.io.IOException;
import java.util.List;

import static com.vulpes.ai.Globals.getMarketByResource;

public class ChartCreator extends JFrame {

    public ChartCreator() throws IOException {

        initUI();
    }

    private void initUI() throws IOException {

        XYDataset marketData = createDataset();
        JFreeChart chart = createChart(marketData);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Line chart");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ChartUtils.saveChartAsPNG(new File("line_chart.png"), chart, 450, 400);
    }

    private XYDataset createDataset() {

        XYSeriesCollection dataset = new XYSeriesCollection();
        for (Resource resource : Resource.values()) {
            XYSeries series = new XYSeries(resource);
            List<Double> hist = getMarketByResource(resource).history;
            for (int i = 0; i < hist.size(); i++) {
                series.add(i + 1, hist.get(i));
            }
            dataset.addSeries(series);
        }
        return dataset;
    }

    private JFreeChart createChart(final XYDataset dataset) {

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Average price per day",
                "Day",
                "Price",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

        int numberOfSeries = 0;
        for (Resource resource : Resource.values()) {
            renderer.setSeriesPaint(numberOfSeries, resource.color);
            renderer.setSeriesStroke(numberOfSeries, new BasicStroke(2.0f));
            numberOfSeries++;
        }

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("Average price per day",
                        new Font("Serif", Font.BOLD, 18)
                )
        );

        return chart;
    }

    public static void createChart() {

        EventQueue.invokeLater(() -> {

            ChartCreator ex = null;
            try {
                ex = new ChartCreator();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ex.setVisible(true);
        });
    }
}
