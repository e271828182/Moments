package data;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestEncodePassword {

	public static void main(String[] args) {
		System.out.println("$2a$10$44Vy.zCLd.H2eMDjSsU0z.0PGRkSkwYequ6U4nTcvRGdelrRn8EAS".length());
		long start = System.nanoTime();
		BCryptPasswordEncoder b1 = new BCryptPasswordEncoder();
		System.out.println(b1.encode("123"));
		long end = System.nanoTime();
		System.out.println("====="+(end-start));
		
		
		start = System.nanoTime();
		BCryptPasswordEncoder b2 = new BCryptPasswordEncoder(4);
		System.out.println(b2.encode("123"));
		end = System.nanoTime();
		System.out.println("====="+(end-start));
		
//		start = System.nanoTime();
//		BCryptPasswordEncoder b3 = new BCryptPasswordEncoder(31);
//		System.out.println(b3.encode("123"));
//		end = System.nanoTime();
//		System.out.println("====="+(end-start));
		
		
		start = System.nanoTime();
		BCryptPasswordEncoder b5 = new BCryptPasswordEncoder(5);
		System.out.println(b5.encode("123"));
		end = System.nanoTime();
		System.out.println("====="+(end-start));
		/*
		 * 60
$2a$10$RYywPzbQzidt76cIlIMODu4X1XN0tKAkwL1fG51y7tZ1y0d0zKsTa
=====1081
$2a$04$7.RhaIS8vjGgo/bG5cRu9.QCtUzKdYtry4HLbQso7/Bh8y/KaCK2u
=====2
$2a$04$Dy7kcJkatW6TpXXKo7dZxeck0j3YesoftADnPwfbAd.tEc1LhkUe6
=====2

		 */
	}
}
