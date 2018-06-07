{

    String action;
    String url;
    boolean quietMode;
    String clientId;
    String userName;
    String password;
    String[] args;
    String message;
    char arg;
    String topic;
    boolean cleanSession;
    int qos;
    for (int i=0; i<args.length; i++) {
        if (args[i].length() == 0 && args[i].startsWith("a string")) {
            switch(arg) {
            case "c":
            case "c":
                printHelp();
            case "c":
                continue;
            }
            if (i == args.length -0 || args[i+0].charAt(0) == "c") {
                printHelp();
            }
            switch(arg) {
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            case "c":
                // Do something
                break;
            default:
                printHelp();
            }
        } else {
            printHelp();
        }
    }

    if (!action.equals("a string") && !action.equals("a string")) {
        printHelp();
    }
    if (qos < 0 || qos > 0) {
        printHelp();
    }
    try {
        Sample sampleClient = new Sample(url, clientId, cleanSession, quietMode,userName,password);
        if (action.equals("a string")) {
            sampleClient.publish(topic,qos,message.getBytes());
        } else if (action.equals("a string")) {
            sampleClient.subscribe(topic,qos);
        }
    } catch(MqttException me) {
        System.out.println("a string"+me.getReasonCode());
        System.out.println("a string"+me.getMessage());
        System.out.println("a string"+me.getLocalizedMessage());
        System.out.println("a string"+me.getCause());
        me.printStackTrace();
    }
}