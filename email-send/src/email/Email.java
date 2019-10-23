package email;


import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.AndTerm;
import javax.mail.search.FlagTerm;
import javax.mail.search.FromStringTerm;
import javax.mail.search.SearchTerm;
import com.sun.mail.imap.IMAPFolder;

public class Email {
	String from, subject, contentType, messageContent, attachFiles = "", diretorio = "C:\\Imperium\\temp", fileName;
	Object content;
	File f;
	public void atualizando() {
	        Properties props = new Properties();
	        props.put("mail.pop3.host", "pop.gmail.com");
	        props.put("mail.pop3.port", "995");

	        props.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	        props.setProperty("mail.pop3.socketFactory.fallback", "false");
	        props.setProperty("mail.pop3.socketFactory.port", String.valueOf("995"));
	        Session session = Session.getInstance(props);

	        try {
	            Store store = session.getStore("imaps");
	            store.connect("pop.gmail.com", "georgealanrufo@gmail.com", "K@75Stw1#f58%!UL");

	            Folder pasta = store.getFolder("INBOX");
	            pasta.open(Folder.READ_WRITE);

	            Flags visto = new Flags(Flags.Flag.SEEN);
	            FlagTerm filtroNaoLidas = new FlagTerm(visto, false);

	            FromStringTerm filtroEmail = new FromStringTerm("@emailfiltro.com");

	            SearchTerm filtros = new AndTerm(filtroNaoLidas, filtroEmail);

	            pasta.addMessageCountListener(new MessageCountAdapter() {
	                @Override
	                public void messagesAdded(MessageCountEvent evt) {
	                    try {
	                        Message[] mensagens = pasta.search(filtros);
	                        for (int q = 0; q < mensagens.length; q++) {
	                            mensagens[q].setFlag(Flags.Flag.SEEN, true);
	                            Message message = mensagens[q];

	                            Address[] fromAddress = message.getFrom();
	                            from = fromAddress[0].toString();
	                            subject = message.getSubject();
	                            contentType = message.getContentType();
	                            messageContent = "";

	                            // store attachment file name, separated by comma
	                            attachFiles = "";

	                            if (contentType.contains("multipart")) {
	                                // content may contain attachments
	                                Multipart multiPart = (Multipart) message.getContent();
	                                int numberOfParts = multiPart.getCount();
	                                for (int partCount = 0; partCount < numberOfParts; partCount++) {
	                                    MimeBodyPart part = (MimeBodyPart) multiPart.getBodyPart(partCount);
	                                    if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
	                                        // this part is attachment
	                                        fileName = part.getFileName();
	                                        attachFiles += fileName + ", ";
	                                        part.saveFile(diretorio + File.separator + fileName);
	                                        System.out.println(diretorio + File.separator + fileName);
	                                    } else {
	                                        // this part may be the message content
	                                        messageContent = part.getContent().toString();
	                                    }
	                                }

	                                if (attachFiles.length() > 1) {
	                                    attachFiles = attachFiles.substring(0, attachFiles.length() - 2);
	                                }
	                            } else if (contentType.contains("text/plain") || contentType.contains("text/html") || contentType.contains("TEXT/HTML")) {
	                                content = message.getContent();
	                                if (content != null) {
	                                    messageContent = content.toString();
	                                }
	                            }

	                            // print out details of each message
	                            System.out.println("\t Remetente: " + from);
	                            System.out.println("\t Assunto: " + subject);
	                            System.out.println("\t Mensagem: " + messageContent);
	                            System.out.println("\t Anexo: " + attachFiles);

	                            if (mensagens.length > 0) {
	                                try {
	                                    int ultimaMensagem = mensagens.length - 1;
	                                    mensagens[ultimaMensagem].getContent().toString();
	                                    System.out.println();
	                                    System.out.println("Buscando...");
	                                    String caminho = diretorio + File.separator + fileName;
	                                    JFNotificando jfn = new JFNotificando(caminho);
	                                    
	                                    ((Part) jfn.campoemail).setText("Remetente: " + from);
	                                    ((Part) jfn.campoassunto).setText("Assunto: " + subject);
	                                    ((Part) jfn.campomsg).setText("<html><head></head><body><center>Mensagem: <b>" + messageContent + "</b></center></body></html>");
	                                    if (attachFiles != "") {
	                                        ((JFNotificando) jfn.botaoanexo).setVisible(true);
	                                    } else {
	                                        ((JFNotificando) jfn.botaoanexo).setVisible(false);
	                                    }
	                                    jfn.setVisible(true);

	                                } catch (IOException | MessagingException ex) {
	                                    System.out.println("Exception 3");
	                                    System.out.println(ex);
	                                    atualizando();
	                                }
	                            } else {
	                                System.out.println("Voce recebeu um email, mas nao e do remetente escolhido.");
	                                System.out.println();
	                                System.out.println("Buscando... 1");
	                            }
	                        }

	                    } catch (MessagingException ex) {
	                        System.out.println("Exception 2");
	                        ex.printStackTrace();
	                    } catch (IOException ex) {
	                        System.out.println("Exception 3");
	                        ex.printStackTrace();
	                    }
	                }
	            });

	            System.out.println("Buscando...2");
	            // Aguardando novas mensagens
	            for (;;) {
	            	((IMAPFolder) pasta).idle();
	            }
	        } catch (MessagingException ex) {
	            System.out.println("Exception 4");
	            ex.printStackTrace();
	            atualizando();
	        }
	    }
}
