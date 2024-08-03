//package com.example.meetpro.jwt;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import com.example.meetpro.domain.Member;
//import com.example.meetpro.dto.CustomSecurityUserDetails;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//public class JWTFilter extends OncePerRequestFilter {
//
//    private final JWTUtil jwtUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        // request에서 Authorization 헤더 찾음
//        String authorization = request.getHeader("Authorization");
//
//        // Authorization 헤더 검증
//        if (authorization == null || !authorization.startsWith("Bearer ")) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Authorization에서 Bearer 접두사 제거
//        String token = authorization.split(" ")[1];
//
//        // token 소멸 시간 검증
//        if (jwtUtil.isExpired(token)) {
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // 최종적으로 token 검증 완료 => 일시적인 session 생성
//        String loginId = jwtUtil.getLoginId(token);
//        String role = jwtUtil.getRole(token);
//
//        Member member = new Member();
//        member.setLoginId(loginId);
//        member.setPassword("임시 비밀번호");
//        member.setRole(role);
//
//        CustomSecurityUserDetails customUserDetails = new CustomSecurityUserDetails(member);
//
//        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
//
//        SecurityContextHolder.getContext().setAuthentication(authToken);
//
//        filterChain.doFilter(request, response);
//    }
//}