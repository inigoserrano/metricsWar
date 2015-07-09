package com.inigoserrano.metrics.metrics;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

/**
 * Servlet implementation class Prueba
 */
@WebServlet("/Prueba")
public class Prueba extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final MetricRegistry metrics = MyMetricsServletContextListener.METRIC_REGISTRY;
	private final Meter requests = metrics.meter("contador");
	private final Timer responses = metrics.timer("tiempos");

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Prueba() {
		super();

		final JmxReporter reporter = JmxReporter.forRegistry(metrics).build();
		reporter.start();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Timer.Context context = responses.time();
		MiLogica logica = new MiLogica();
		requests.mark();
		response.getWriter().append(logica.respuesta());
		response.flushBuffer();
		context.stop();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
