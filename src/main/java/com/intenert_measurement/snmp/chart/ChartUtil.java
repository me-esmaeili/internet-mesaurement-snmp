package com.intenert_measurement.snmp.chart;

import com.intenert_measurement.snmp.ifnet.InterfaceMetrics;
import com.intenert_measurement.snmp.metric.Aggregator;
import com.intenert_measurement.snmp.metric.Metric;
import com.intenert_measurement.snmp.metric.MetricType;
import com.intenert_measurement.snmp.metric.MetricUtil;
import com.intenert_measurement.snmp.util.HostSnmpConnectionInfo;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class ChartUtil {

    public static void saveAndShowResults(InterfaceMetrics interfaceMetrics, boolean saveResult) {
        for (Metric metric : interfaceMetrics.getMetrics()) {
            metric.getHost().setName(interfaceMetrics.getIp()); // only for show in chart
        }
        saveAndShowResults(interfaceMetrics.getMetrics(), saveResult);
    }

    public static void saveAndShowResults(List<Metric> metrics, boolean saveResult) {
        if (metrics.isEmpty()) {
            return;
        }
        List<ChartTotalInfo> chartTotalInfos = new ArrayList<>();

        Map<MetricType, List<Metric>> groupByType = metrics.stream().collect(Collectors.groupingBy(Metric::getType));
        for (Map.Entry<MetricType, List<Metric>> typeGroup : groupByType.entrySet()) {
            Map<String, ChartSeriesInfo> hostToRawValues = new HashMap<>();
            Map<HostSnmpConnectionInfo, List<Metric>> hostChart = typeGroup.getValue().stream().collect(Collectors.groupingBy(Metric::getHost));
            // type different chart for hosts
            for (Map.Entry<HostSnmpConnectionInfo, List<Metric>> metricsEntry : hostChart.entrySet()) {
                hostToRawValues.put(metricsEntry.getKey().getName() + "-" + typeGroup.getKey().name() + "-Raw", new ChartSeriesInfo(
                                metricsEntry.getValue().stream().map(Metric::getTimestamp).collect(Collectors.toList()),
                                metricsEntry.getValue().stream().map(x -> x.getType() == MetricType.UPTIME ?
                                        x.getAvailability() : x.toNumber()).collect(Collectors.toList())
                        )
                );
                hostToRawValues.put(metricsEntry.getKey().getName() + "-" + typeGroup.getKey().name() + "-Avg", new ChartSeriesInfo(
                                metricsEntry.getValue().stream().map(Metric::getTimestamp).collect(Collectors.toList()),
                                MetricUtil.computeAggregation(
                                        metricsEntry.getValue(),
                                        (x -> x.getType() == MetricType.UPTIME ? x.getAvailability() : (Number) x.toNumber()),
                                        Aggregator.AVERAGE
                                ).stream().map(x -> (Number) x.getValue()).collect(Collectors.toList())
                        )
                );
            }
            chartTotalInfos.add(new ChartTotalInfo(typeGroup.getKey().name(), hostToRawValues));
        }

        for (ChartTotalInfo singleChartInfo : chartTotalInfos) {
            String chartName = singleChartInfo.getChartName();
            XYChart chart = new XYChart(800, 600);
            chart.setTitle(chartName);
            chart.setXAxisTitle("Time");
            chart.setYAxisTitle("Value");
            singleChartInfo.getSeriesNameAndInfoMap().forEach((seriesKey, seriesInfo) -> chart.addSeries(seriesKey, seriesInfo.getXData(), seriesInfo.getYData()));
            if (saveResult) {
                try {
                    BitmapEncoder.saveBitmapWithDPI(chart, singleChartInfo.getChartName(), BitmapEncoder.BitmapFormat.PNG, 300);
                } catch (IOException ex) {
                    log.error("Error while saving chart image file", ex);
                }
            }
            new SwingWrapper<>(chart).displayChart();
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ChartSeriesInfo {
        private List<Date> xData;
        private List<Number> yData;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class ChartTotalInfo {
        private String chartName;
        private Map<String, ChartSeriesInfo> seriesNameAndInfoMap;
    }
}
