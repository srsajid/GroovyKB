package serial.com;

import jssc.*;

import java.io.IOException;
import java.util.Scanner;

public class JSSCTest {
    private static SerialPort serialPort;

    public static void main(String[] args) throws InterruptedException {
        String[] portNames = SerialPortList.getPortNames();

        if (portNames.length == 0) {
            System.out.println("There are no serial-ports :( You can use an emulator, such ad VSPE, to create a virtual serial port.");
            System.out.println("Press Enter to exit...");
            try {
                System.in.read();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return;
        }

        // выбор порта
        System.out.println("Available com-ports:");
        for (int i = 0; i < portNames.length; i++){
            System.out.println(portNames[i]);
        }
        System.out.println("Type port name, which you want to use, and press Enter...");
        Scanner scanner = new Scanner(System.in);

        // writing to port
        serialPort = new SerialPort("COM6");
        try {
            // opening port
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            serialPort.setRTS(true);
            serialPort.setDTR(true);
            serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN |
                    SerialPort.FLOWCONTROL_RTSCTS_OUT);

//            serialPort.addEventListener(new PortReader(), SerialPort.MASK_RXCHAR);
            // writing string to port
            serialPort.writeString("AT+CMGF=1\r"); // Set mode to Text(1) or PDU(0)
            Thread.sleep(1000); // Give a second to write
            serialPort.writeString("AT+CMGS=\"01672178618\"\r"); // Set storage to SIM(SM)
            Thread.sleep(1000);
            serialPort.writeString("Hello World" + Character.toString((char) 26) + "\r"); // What category to read ALL, REC READ, or REC UNREAD
            Thread.sleep(5000);
            String s  = new String(serialPort.readBytes());

            System.out.println(s);
        }
        catch (SerialPortException ex) {
            System.out.println("Error in writing data to port: " + ex);
        }
    }

    private static class PortReader implements SerialPortEventListener {

        @Override
        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0) {
                try {
                    // получение ответа от порта
                    String receivedData = serialPort.readString(event.getEventValue());
                    System.out.println("Received response from port: " + receivedData);
                }
                catch (SerialPortException ex) {
                    System.out.println("Error in receiving response from port: " + ex);
                }
            }
        }
    }
}
