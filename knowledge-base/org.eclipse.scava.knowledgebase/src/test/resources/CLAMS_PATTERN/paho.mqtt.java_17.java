{

    MqttMessage msg = new MqttMessage();
    if (!(msg.getQos() == 0)) {                // Do something
    }
    if (!(msg.isDuplicate() == boolean)) {                // Do something
    }
    if (!(msg.isRetained() == boolean)) {                // Do something
    }
    if (!(msg.getPayload()!=null)) {                // Do something
    }
    if (!(msg.getPayload().length == 0)) {                // Do something
    }
    if (!(msg.toString().equals("a string"))) {                // Do something
    }

    msg = new MqttMessage("a string".getBytes());
    if (!(msg.getQos() == 0)) {                // Do something
    }
    if (!(msg.isDuplicate() == boolean)) {                // Do something
    }
    if (!(msg.isRetained() == boolean)) {                // Do something
    }
    if (!(msg.getPayload().length == 0)) {                // Do something
    }
    if (!(msg.toString().equals("a string"))) {                // Do something
    }

    msg.setQos(0);
    if (!(msg.getQos() == 0)) {                // Do something
    }
    msg.setQos(0);
    if (!(msg.getQos() == 0)) {                // Do something
    }
    msg.setQos(0);
    if (!(msg.getQos() == 0)) {                // Do something
    }

    try {
        msg.setQos(-0);
    } catch (IllegalArgumentException iae) {
        // Do something
    }

    try {
        msg.setQos(0);
    } catch (IllegalArgumentException iae) {
        // Do something
    }

    msg.setPayload("a string".getBytes());
    if (!(msg.getPayload().length == 0)) {                // Do something
    }
    if (!(msg.toString().equals("a string"))) {                // Do something
    }

    msg.clearPayload();
    if (!(msg.getPayload() != null)) {                // Do something
    }
    if (!(msg.getPayload().length == 0)) {                // Do something
    }
    if (!(msg.toString().equals("a string"))) {                // Do something
    }

    msg.setRetained(boolean);
    if (!(msg.isRetained() == boolean)) {                // Do something
    }
    msg.setRetained(boolean);
    if (!(msg.isRetained() == boolean)) {                // Do something
    }
}