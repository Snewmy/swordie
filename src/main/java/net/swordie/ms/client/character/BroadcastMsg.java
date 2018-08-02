package net.swordie.ms.client.character;

import net.swordie.ms.client.character.items.Item;
import net.swordie.ms.connection.OutPacket;
import net.swordie.ms.enums.BroadcastMsgType;

import java.util.List;

/**
 * Created by Asura on 17-6-2018.
 */
public class BroadcastMsg {
    BroadcastMsgType broadcastMsgType;
    Item item;
    String string;
    String string2;
    String string3;
    byte arg1;
    byte arg2;
    byte arg3;

    public void encode(OutPacket outPacket) {
        outPacket.encodeByte(getBroadcastMsgType().getVal());

        outPacket.encodeString(getString());

        switch (getBroadcastMsgType()) {
            case Notice:
            case PopUpMessage:
            case DarkBlueOnLightBlue:
            case PartyChat:
                break;
            case Megaphone:
            case MegaphoneNoMessage:
                outPacket.encodeByte(getArg1()); // Channel
                outPacket.encodeByte(getArg2()); // Mega Ear
                break;
            case ItemMegaphone:
                outPacket.encodeByte(getArg1()); // Channel
                outPacket.encodeByte(getArg2()); // Mega Ear
                outPacket.encodeByte(getArg3()); // Boolean  Item: Yes/No
                if(getArg3() != 0) {
                    getItem().encode(outPacket); // Item encode
                }
                break;
            case TripleMegaphone:
                outPacket.encodeByte(getArg1()); // StringList size
                if(getArg1() > 1) {
                    outPacket.encodeString(getString2()); // String 2
                }
                if(getArg1() > 2) {
                    outPacket.encodeString(getString3()); // String 3
                }
                outPacket.encodeByte(getArg2()); // Channel
                outPacket.encodeByte(getArg3()); // Mega Ear
                break;
            case WhiteYellow:
                outPacket.encodeByte(getArg1()); // Boolean  Item: Yes/No
                if(getArg1() != 0) {
                    getItem().encode(outPacket); // Item encode
                }
                break;
            case Yellow:
                getItem().encode(outPacket); // Item encode
                break;
        }
    }

    public static BroadcastMsg itemMegaphone(String string, byte channel, boolean whisperEar, boolean containsItem, Item item) {
        BroadcastMsg broadcastMsg = new BroadcastMsg();
        broadcastMsg.setBroadcastMsgType(BroadcastMsgType.ItemMegaphone);

        broadcastMsg.setString(string);
        broadcastMsg.setArg1((byte) (channel - 1));
        broadcastMsg.setArg2((byte) (whisperEar ? 1 : 0));
        broadcastMsg.setArg3((byte) (containsItem ? 1 : 0));
        broadcastMsg.setItem(item);

        return broadcastMsg;
    }

    public static BroadcastMsg tripleMegaphone(List<String> stringList, byte channel, boolean whisperEar) {
        BroadcastMsg broadcastMsg = new BroadcastMsg();
        broadcastMsg.setBroadcastMsgType(BroadcastMsgType.TripleMegaphone);

        String nexonIsFuckingBrainDead = "  ";

        broadcastMsg.setArg1((byte) stringList.size());
        broadcastMsg.setString(stringList.get(0));
        if(stringList.size() > 1) {
            broadcastMsg.setString2(nexonIsFuckingBrainDead + stringList.get(1));
        }
        if(stringList.size() > 2) {
            broadcastMsg.setString3(nexonIsFuckingBrainDead + stringList.get(2));
        }
        broadcastMsg.setArg2((byte) (channel - 1));
        broadcastMsg.setArg3((byte) (whisperEar ? 1 : 0));

        return broadcastMsg;
    }

    public static BroadcastMsg megaphone(String string, byte channel, boolean whisperEar) {
        BroadcastMsg broadcastMsg = new BroadcastMsg();
        broadcastMsg.setBroadcastMsgType(BroadcastMsgType.Megaphone);

        broadcastMsg.setString(string);
        broadcastMsg.setArg1((byte) (channel - 1));
        broadcastMsg.setArg2((byte) (whisperEar ? 1 : 0));

        return broadcastMsg;
    }

    public static BroadcastMsg notice(String string) {
        BroadcastMsg broadcastMsg = new BroadcastMsg();
        broadcastMsg.setBroadcastMsgType(BroadcastMsgType.Notice);

        broadcastMsg.setString(string);

        return broadcastMsg;
    }

    public static BroadcastMsg popUpMessage(String string) {
        BroadcastMsg broadcastMsg = new BroadcastMsg();
        broadcastMsg.setBroadcastMsgType(BroadcastMsgType.PopUpMessage);

        broadcastMsg.setString(string);

        return broadcastMsg;
    }


    public BroadcastMsgType getBroadcastMsgType() {
        return broadcastMsgType;
    }

    public void setBroadcastMsgType(BroadcastMsgType broadcastMsgType) {
        this.broadcastMsgType = broadcastMsgType;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public byte getArg1() {
        return arg1;
    }

    public void setArg1(byte arg1) {
        this.arg1 = arg1;
    }

    public byte getArg2() {
        return arg2;
    }

    public void setArg2(byte arg2) {
        this.arg2 = arg2;
    }

    public byte getArg3() {
        return arg3;
    }

    public void setArg3(byte arg3) {
        this.arg3 = arg3;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
