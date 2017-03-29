/* Copyright(C) 2015 Interactive Health Solutions, Pvt. Ltd.

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as
published by the Free Software Foundation; either version 3 of the License (GPLv3), or any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

See the GNU General Public License for more details. You should have received a copy of the GNU General Public License along with this program; if not, write to the Interactive Health Solutions, info@ihsinformatics.com
You can also access the license on the internet at the address: http://www.gnu.org/licenses/gpl-3.0.html

Interactive Health Solutions, hereby disclaims all copyright interest in this program written by the contributors.

Contributors: Tahira Niazi */
/**
 * This activity is used to display line/bar graph
 */

package com.ihsinformatics.childhoodtb_mobile;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.ihsinformatics.childhoodtb_mobile.util.GraphUtil;
import com.ihsinformatics.childhoodtb_mobile.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphView.GraphViewData;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.GraphViewSeries.GraphViewSeriesStyle;

public class GraphActivity extends Activity {
	public static final String TITLE = "TITLE";
	public static final String X_DATA = "X_DATA";
	public static final String Y_DATA = "Y_DATA";
	LinearLayout graphLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		String title = extras.getString(TITLE);
		double[] xData = extras.getDoubleArray(X_DATA);
		double[] yData = extras.getDoubleArray(Y_DATA);
		graphLayout = (LinearLayout) findViewById(R.graph_id.graphLinearLayout);
		showGraph(title, xData, yData);
	}

	public void showGraph(String graphName, double[] xValues, double[] yValues) {
		GraphView graphView = GraphUtil.getLineGraph(GraphActivity.this,
				graphName);
		GraphViewData[] graphData = new GraphViewData[xValues.length];
		for (int i = 0; i < xValues.length; i++) {
			graphData[i] = new GraphViewData(xValues[i], yValues[i]);
		}
		GraphViewSeries graphSeries = new GraphViewSeries(graphName,
				new GraphViewSeriesStyle(Color.BLUE, 1), graphData);
		graphView.addSeries(graphSeries);
		graphLayout.addView(graphView);
	}
}
