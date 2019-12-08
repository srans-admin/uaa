package com.srans.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

 
@Configuration
@EnableAuthorizationServer
public class OAuth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	@Value("${clientid:foo}")
	private String clientid;
	
	@Value("${clientSecret:bar}")
	private String clientSecret;
	
	@Value("${accessTokenValiditySeconds:20000}")
	private int accessTokenValiditySeconds;
	
	@Value("${refreshTokenValiditySeconds:20000}")
	private int refreshTokenValiditySeconds;
	
	private String privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" + 
	   		"MIIEpAIBAAKCAQEAuainvFxGvfV9Zkj5JNRvdsUdU8ZcfjL1LrRWnodW7kyEcMTQ\n" + 
	   		"6Vp5/RVVVqYTDSOklxsZJsNUlexzjrzX2Nu+UpdzxU0a3Lpwe9ynt/X15Lem5X2K\n" + 
	   		"So+VqpP57Fmo/9WrAFeKxH25QxJMyXtqkmEUADVyEN6mX35oEOYo4psqRKckyfg7\n" + 
	   		"afu8Q7VuLhd8C+hkG4z6E1qjnlPvGRREybCku/vtGGoDOQXC5tW+PdBZmYdaSFf0\n" + 
	   		"Vac83LJ5ZrCvv8Xw/IdsyV7suyYmy/xrcdRK0Ef/KkWwvmg52FAmBbBCpD5qKx6T\n" + 
	   		"jY6KZMC1oiaT5HCeG19Gl/8/KOhhLAFghPwodwIDAQABAoIBACzSaNxysql9sO6W\n" + 
	   		"w39+wgI8iF4HT6EwED1cUGUpa3VQh9Dp0z3jF/xfwCCRK5xKdJv+cM4I7kzbrvJQ\n" + 
	   		"6RNzybkK02pYuRl88VcV6G/jM6AqLmLNMivaCloKK5ZB9yj8nrIQcHILoERvEJa7\n" + 
	   		"8viv8zIxtP4ZorT/QYSC7G/44fV8s3+ReqvuiVR/38z9lvZw7RbNmmtT48DmEAG3\n" + 
	   		"usFLEG6A0BsO4hsc3OC4Iy8Gjap9mWVEKAX7zY8NmCplNun2erXiThUqI3DB/Spc\n" + 
	   		"+xPynY03+9W9Z9N1Hf4Ly9x7uimlCTPycHQOnXnV7K1fu7DwDagx6kdQrasVS5fz\n" + 
	   		"fxDB32ECgYEA3XKDF+FIwikEOJN2Xacn0vav1JmtTZuXjalrHABRDaY5UCpqW0UI\n" + 
	   		"tY8771KAR6+Wc815f80XtLRZ0rZH2zpKdCylwe/xvOpFPuzD0TCFY5+5VtANSWmX\n" + 
	   		"x3cZU8mpGYbsfZm9N5eLUF+zBRWyj1/yu6G9iogsjgN4znXA7FqG7E8CgYEA1qCb\n" + 
	   		"lB+8cCMFiOJ6e0xzn1q7aFDmA2IBtqTEj4NZafMlsIJzSrlWI61nMuSNxPHhkEJH\n" + 
	   		"UCiVbcU1fQq8ffB5cB78PEik7utsSBKS9fLGg4YHDVo7jLFyGAtNP4rEOssS58uJ\n" + 
	   		"eqmgLiLmK3aagkH2tRHsxRICDRW5w32aqDJWr1kCgYEAng31cz3gaHROBvN5MC72\n" + 
	   		"RaS8qslk4FeFPB8Hy9yN/Wf+0DD16F7FcJDi/lWsKX4xrEWjyovft6Jh2fswHlDk\n" + 
	   		"gza6uElCCMxM0DS9Lrmq+wgtiMDGOvea1Us0UJFGmGmssZjhbKRFUwB7Dc4GvD6H\n" + 
	   		"SVSPrCi1ZEcmGnYBVLDoTBECgYAGhgs0jWJk5iuc5YVjigdZqKBZo1vabngu3QM8\n" + 
	   		"eXmWRtW2b8ChXNDJdNPWMCw++Dsq71jLjvA7zeq6SkFAcra+ObLhAjRBLtazuhzN\n" + 
	   		"bPurs78zILL+q8YUBQPsEoO0ZcfFU+Jx4AYGYPkuCQTZQLgG+OyBcPKvAyIKVx8E\n" + 
	   		"TcNkwQKBgQDT+39/XG/AcAToLyvv3LR+HJ8+8C7AQCgcHz67fDMBKP+3Cu6bEHbO\n" + 
	   		"Zkm/GZZaQGrGyAOzt0iYEudmKRBb3Xq5CYRFJbrQ+vRsWOChZzO77+kvRzVO8AKK\n" + 
	   		"6gDTvV71AWE1kH9nStF8Ep5b3kDr30KdWJI7LAYB/bdeqvcDA4smJg==\n" + 
	   		"-----END RSA PRIVATE KEY-----";
	
	 private String publicKey = "-----BEGIN PUBLIC KEY-----\n" + 
	   		"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAuainvFxGvfV9Zkj5JNRv\n" + 
	   		"dsUdU8ZcfjL1LrRWnodW7kyEcMTQ6Vp5/RVVVqYTDSOklxsZJsNUlexzjrzX2Nu+\n" + 
	   		"UpdzxU0a3Lpwe9ynt/X15Lem5X2KSo+VqpP57Fmo/9WrAFeKxH25QxJMyXtqkmEU\n" + 
	   		"ADVyEN6mX35oEOYo4psqRKckyfg7afu8Q7VuLhd8C+hkG4z6E1qjnlPvGRREybCk\n" + 
	   		"u/vtGGoDOQXC5tW+PdBZmYdaSFf0Vac83LJ5ZrCvv8Xw/IdsyV7suyYmy/xrcdRK\n" + 
	   		"0Ef/KkWwvmg52FAmBbBCpD5qKx6TjY6KZMC1oiaT5HCeG19Gl/8/KOhhLAFghPwo\n" + 
	   		"dwIDAQAB\n" + 
	   		"-----END PUBLIC KEY-----";
 
	   
	   @Bean
	   public JwtAccessTokenConverter tokenEnhancer() {
	      JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
	      converter.setSigningKey(privateKey);
	      converter.setVerifierKey(publicKey);
	      return converter;
	   } 
	   
	
	   @Bean
	   public JwtTokenStore tokenStore() {
	      return new JwtTokenStore(tokenEnhancer());
	   }
	
	   @Override
	   public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
	      endpoints
	      .authenticationManager(authenticationManager)
	      .tokenStore(tokenStore())
	      .accessTokenConverter(tokenEnhancer());
	   }
	   
	   @Override
	   public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
	      security.tokenKeyAccess("permitAll()")
	      .checkTokenAccess("isAuthenticated()");
	   }
	   
	   @Override
	   public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		   configurer.inMemory()
			   .withClient(clientid)
			   .secret(clientSecret)
			   .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT")
			   .authorizedGrantTypes("password", "refresh_token", "client_credentials")
			   .scopes("read","write")
			   .accessTokenValiditySeconds(accessTokenValiditySeconds)
			   .refreshTokenValiditySeconds(refreshTokenValiditySeconds); 

	   }
}
