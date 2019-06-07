package org.eclipse.scava.platform.communicationchannel.mbox.encoding;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import javax.mail.MessagingException;
import javax.mail.internet.MimeUtility;

import org.apache.commons.io.IOUtils;

public class UTF8toASCII {

	public UTF8toASCII() {
		// TODO Auto-generated constructor stub
	}

	public static String encodeQP(String text) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStream encodedOut = null;
		try {
			encodedOut = MimeUtility.encode(baos, "quoted-printable");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		try {
			encodedOut.write(text.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return baos.toString();
	}

	public static String decodeQP(String text) {
		byte[] b = null;
		try {
			b = text.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		ByteArrayInputStream bais = new ByteArrayInputStream(b);
		InputStream is = null;
		try {
			is = MimeUtility.decode(bais, "quoted-printable");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		StringWriter writer = new StringWriter();
		try {
			IOUtils.copy(is, writer);//, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

	public static String encodeTextQP(String original) {
		String encoded = null;
		try {
			encoded = MimeUtility.encodeText(original, "UTF-8", "Q");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encoded;
	}

	public static void main(String[] args) {
//		String original = "I am alive! Erv�lia, JO�O, ASSUN��O, Catura�";
		String original = 
//						  "From QNeJkpU1JPEu7VZE@TqW7Vt0dL44JzNJ5  Wed Jun  5 15:11:41 2013\n" + 
//						  "Return-Path: <QNeJkpU1JPEu7VZE@TqW7Vt0dL44JzNJ5>\n" + 
//						  "X-Original-To: CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7\n" + 
//						  "Delivered-To: CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7\n" + 
//						  "Received: from pop-24.pop.com.br (mail-out-4.pop.com.br [200.175.8.210])\n" + 
//						  "	by mail.eclipse.org (Postfix) with ESMTP id C5AFF2DCD10\n" + 
//						  "	for <CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7>; Wed,  5 Jun 2013 15:11:20 -0400 (EDT)\n" + 
//						  "Received: (qmail 9485 invoked from network); 5 Jun 2013 19:11:20 -0000\n" + 
//						  "Received: from 177.42.127.64.static.host.gvt.net.br (HELO cw-472cb77f73d0.host)\n" + 
//						  "	(VHp5NnuDxR5C78sH@VjTS6z03Sk1fiWfu@[177.42.127.64])\n" + 
//						  "	(envelope-sender <QNeJkpU1JPEu7VZE@TqW7Vt0dL44JzNJ5>)\n" + 
//						  "	by pop-24.pop.com.br (qmail-ldap-1.03) with SMTP\n" + 
//						  "	for <CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7>; 5 Jun 2013 19:11:20 -0000\n" + 
//						  "X-C3Mail-ID: 1370459480279027\n" + 
//						  "From: \"David Anderson\" <QNeJkpU1JPEu7VZE@TqW7Vt0dL44JzNJ5>\n" + 
//						  "To: CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7\n" + 
//						  "Subject: Erv�lia - Nomes dos aprovados\n" + 
//						  "Date: Wed, 5 Jun 2013 16:11:14 +0430\n" + 
//						  "MIME-Version: 1.0\n" + 
//						  "Message-ID: <bcs8HK/hmSR4tdkr@TqW7Vt0dL44JzNJ5>\n" + 
//						  "Content-Type: text/plain;\n" + 
//						  "	charset=\"iso-8859-1\"\n" + 
//						  "Content-Transfer-Encoding: 8bit\n" + 
//						  "X-Remote-IP: 177.42.127.64\n" + 
//						  "X-BeenThere: CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7\n" + 
//						  "X-Mailman-Version: 2.1.14\n" + 
//						  "Precedence: list\n" + 
//						  "Reply-To: Apricot Project developer discussions <CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7>\n" + 
//						  "List-Id: Apricot Project developer discussions <apricot-dev.eclipse.org>\n" + 
//						  "List-Unsubscribe: <http://dev.eclipse.org/mailman/options/apricot-dev>,\n" + 
//						  "	<mailto:FE5b/rL87lDYWiWi@io0ogF2PRwNErzz7?subject=unsubscribe>\n" + 
//						  "List-Archive: <http://dev.eclipse.org/mailman/private/apricot-dev>\n" + 
//						  "List-Post: <mailto:CXrlB8FRJrs3cTOK@io0ogF2PRwNErzz7>\n" + 
//						  "List-Help: <mailto:FE5b/rL87lDYWiWi@io0ogF2PRwNErzz7?subject=help>\n" + 
//						  "List-Subscribe: <http://dev.eclipse.org/mailman/listinfo/apricot-dev>,\n" + 
//						  "	<mailto:FE5b/rL87lDYWiWi@io0ogF2PRwNErzz7?subject=subscribe>\n" + 
//						  "X-List-Received-Date: Wed, 05 Jun 2013 19:11:41 -0000\n" + 
//						  "\n" + 
						  "Erv�lia  ANNE KAROLYNE PEREIRA DA SILVA, LUCAS FERREIRA DANTAS, GERLAN LOPES DA COSTA, " + 
//						  "RAFAELA SOARES DOS ANJOS, JO�O CARLOS MOREIRA DE CARVALHO, DENIS BARROS SILVA, MARIA MICAELE " + 
//						  "ALVES DE MELO, JOEL FERREIRA MADUREIRA. TAYANE ASSUN��O NEVES, CAMILLA KEILHANY DE SOUSA " + 
//						  "CAETANO, MAIARA MUSY ARAUJO, IALE NAIANE MARIANO DOS PASSOS, ROAN CADMUS RODRIGUES DAMASCENO.  Catura�.\n" + 
//						  "\n" + 
//						  "Banabui� ALANA KELLY AFIO CAETANO, KARLA ALEXSANDRA LEITAO LIMA, FABIANA MAIA DE ALCANTARA, " + 
//						  "M�NICA M�RCIA DE LIMA, JO�O CARLOS MOREIRA DE CARVALHO, CATARINA LABORE VIDAL FERNANDES, " + 
//						  "MARCELA TEOFILO BARROSO, IKARO GABRIEL CAVALCANTE MONTEIRO PINHEIRO. ROM�RIO JOS� REZENDE " + 
//						  "DE BRITO, EDNAIRAN DOS SANTOS MARTINS, MARINALDO MANOEL DA SILVA JUNIOR, JOS� ELYELSON " + 
//						  "MARQUES TORRES, VALCLEIDE DOS SANTOS MENDES. Dom Aquino.\n" + 
//						  "\n" + 
//						  "Areado ANNE KAROLYNE PEREIRA DA SILVA, LUCAS FERREIRA DANTAS, GERLAN LOPES DA COSTA, RAFAELA " + 
//						  "SOARES DOS ANJOS, JO�O CARLOS MOREIRA DE CARVALHO, DENIS BARROS SILVA, MARIA MICAELE ALVES " + 
//						  "DE MELO, JOEL FERREIRA MADUREIRA. TAYANE ASSUN��O NEVES, CAMILLA KEILHANY DE SOUSA CAETANO, " + 
//						  "MAIARA MUSY ARAUJO, IALE NAIANE MARIANO DOS PASSOS, ROAN CADMUS RODRIGUES DAMASCENO. Dom Aquino.\";\n" + 
						  "\n";
		String[] lines = original.split("\n");
		for (String line: lines) {
			if (isPureAscii(line)) {
				System.out.println("remains unchanged: |" + line + "|");
			} else {
				System.out.println("line: |" + line + "|");
				String encoded = encodeQP(line);
				System.out.println("encoded: |" + encoded + "|");
				System.out.println("encoded no new lines: |" + removeSoftNewLines(encoded) + "|");
				System.out.println("decoded: |" + decodeQP(encoded) + "|");
				System.out.println("encodedText: |" + encodeTextQP(line) + "|");
			}
			System.out.println();
		}
		
	}

	public static String removeSoftNewLines(String encoded) {
		return encoded.replace("=\r\n", "");
	}

	public static boolean isPureAscii(String line) {
		return Charset.forName("US-ASCII").newEncoder().canEncode(line);
	}
	
	
}
