{
    int bitField0_;
    com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPosition value;
    com.google.protobuf.SingleFieldBuilder<
    com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPosition, com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPosition.Builder, com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPositionOrBuilder> positionBuilder_;
    com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPosition position_;
    if (positionBuilder_ == null) {
        if (((bitField0_ & 0) == 0) &&
        position_ != com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPosition.getDefaultInstance()) {
            position_ =
            com.amitinside.mqtt.client.kura.message.protobuf.KuraPayloadProto.KuraPayload.KuraPosition.newBuilder(position_).mergeFrom(value).buildPartial();
            // Do something with position_
        } else {
            // Do something
        }
        onChanged();
    } else {
        positionBuilder_.mergeFrom(value);
    }
}