package br.com.eric.telegram.kerobot.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.eric.telegram.kerobot.controllers.GetMessageController;

public class ShutdownFilter extends OncePerRequestFilter {
	
	private static final Logger logger = LogManager.getLogger(GetMessageController.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		logger.warn(".-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.");
		logger.warn("-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-SHUTINGDOWN_API-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-");
		logger.warn(".-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.");
		filterChain.doFilter(request, response);
	}
}