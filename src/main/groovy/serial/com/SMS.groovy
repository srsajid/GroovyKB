package serial.com

import jssc.SerialPort

class SMS {
    String portName
    SerialPort serialPort

    public SMS(String portName) {
        this.portName = portName
        openPort()
    }

    public void openPort() {
        serialPort = new SerialPort(portName);
        serialPort.openPort();

        serialPort.setParams(SerialPort.BAUDRATE_9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        serialPort.setRTS(true);
        serialPort.setDTR(true);
        serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN | SerialPort.FLOWCONTROL_RTSCTS_OUT);
    }
    public Boolean send(String number, String message) {
        serialPort.writeString("AT+CMGF=1\r");
        Thread.sleep(1000);
        serialPort.writeString("AT+CMGS=\"${number}\"\r")
        Thread.sleep(1000);
        serialPort.writeString(message + Character.toString((char) 26) + "\r");
        Thread.sleep(5000);
        String response  = new String(serialPort.readBytes());
        println(response)
        return response.endsWith("\r\nOK\r\n")
    }

    /*
    * Status: “ALL”, “REC UNREAD” or “REC READ”
    * */
    public void read(String status = "ALL") {
        serialPort.writeString("AT+CMGF=1\r");
        Thread.sleep(1000);
        serialPort.writeString("AT+CPMS=\"SM\"\r"); // Set storage to SIM(SM)
        Thread.sleep(1000);
        serialPort.writeString("AT+CMGL=\"${status}\""); // What category to read ALL, REC READ, or REC UNREAD
        Thread.sleep(1000);
        serialPort.writeString("\r")
        Thread.sleep(10000)
        String response  = new String(serialPort.readBytes());
        println(response)
    }

    public void ussd(String serviceCode) {
        serialPort.writeString("AT+CMGF=1\r");
        Thread.sleep(1000);
        serialPort.writeString("AT+CUSD=1,\"${serviceCode}\",15\r")
        Thread.sleep(4000)
        String response  = new String(serialPort.readBytes());
        println(response)
    }

    void closePort() {
        serialPort.closePort()
    }
}
