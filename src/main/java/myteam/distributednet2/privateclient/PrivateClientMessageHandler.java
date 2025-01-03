package myteam.distributednet2.privateclient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import myteam.distributednet2.common.CommonCharcterCode;

public class PrivateClientMessageHandler {
	private Socket socket;
	private OutputStream outputStream;
	private InputStream inputStream;
	private final int bufferSize;
	private static final String MESSAGE_END = "\r\n\r\n";
	private static byte[] MESSAGE_END_BYTES = MESSAGE_END.getBytes(CommonCharcterCode.getCharset());
	private class MessageReciver extends Thread {
		@Override
		public void run() {
			boolean running = true;
			while(running) {
				try {
					byte[] readData = readMessage();
					readMessageAnalyze(readData);
				} catch(IOException e) {
					e.printStackTrace();
					running = false;
				}
			}
			close();
		}

		private byte[] readMessage() throws IOException {
			byte[] readBuf = new byte[bufferSize];
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int readSize = inputStream.read(readBuf);
			byte[] readDataBytes = Arrays.copyOf(readBuf, readSize);
			bos.write(readDataBytes);
			while(!isMessageEnded(readBuf)) {
				readBuf = new byte[bufferSize];
				readSize = inputStream.read(readBuf);
				readDataBytes = Arrays.copyOf(readBuf, readSize);
				bos.write(readBuf);
			}
			return bos.toByteArray();
		}

		private boolean isMessageEnded(byte[] buffer) {
			int recvLen = buffer.length;
			int endLen = MESSAGE_END_BYTES.length;
			if(recvLen < endLen) {
				return false;
			}
			for(int i = 1; i <= endLen; i++) {
				if(buffer[recvLen - i] != MESSAGE_END_BYTES[endLen - i]) {
					return false;
				}
			}
			return true;
		}

		private void readMessageAnalyze(byte[] readData) {
			String readDataStr = new String(readData, CommonCharcterCode.getCharset());
			String[] readDataLine = readDataStr.split("\r\n");

			if("SEARCH".equals(readDataLine[0].trim())) {
				
			} else if("RESULT".equals(readDataLine[0].trim())) {
				
			}
			List<String> data = new ArrayList<>();
		}
	}

	PrivateClientMessageHandler(Socket socket) {
		this.socket = socket;
		this.bufferSize = Integer.parseInt(PrivatePropertiesReader.getValueSttingDataType("readBuffer"));
	}

	void messageHandlerStart() throws IOException {
		this.inputStream = this.socket.getInputStream();
		this.outputStream = this.socket.getOutputStream();

		new MessageReciver().start();
	}

	private void close() {
		try {
			this.inputStream.close();
			this.outputStream.close();
			this.socket.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
}
