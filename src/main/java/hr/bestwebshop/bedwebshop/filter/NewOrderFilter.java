package hr.bestwebshop.bedwebshop.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
@Slf4j
public class NewOrderFilter implements Filter {

    private final static String filePath = "order_creations_info.txt";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if ("POST".equals(request.getMethod()) && request.getRequestURI().equals("/bedswebshop/order/createOrder")) {
            Instant startTime = Instant.now();

            filterChain.doFilter(servletRequest, servletResponse);

            Instant endTime = Instant.now();
            Duration duration = Duration.between(startTime, endTime);

            String orderCreationInfo = "Request started at: " + LocalDateTime.ofInstant(endTime, ZoneOffset.UTC)
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")) + "\n"
                    + "Request duration: " + duration.toMillis() + " milliseconds\n";

            File file = new File(filePath);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file, true);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(orderCreationInfo);
                bw.newLine();
                bw.close();
            } catch (IOException e) {
                log.error("Error: ", e);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
