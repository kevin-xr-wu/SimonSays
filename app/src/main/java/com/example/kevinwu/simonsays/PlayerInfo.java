package com.example.kevinwu.simonsays;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Kevin on 6/26/2016.
 */
public class PlayerInfo implements Parcelable{

    private String playerName;
    private int playerScore;

    public PlayerInfo(){

    }

    public PlayerInfo(JSONObject jsonObject){
        try{
            playerName = jsonObject.getString("Name");
            playerScore = jsonObject.getInt("Score");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int describeContents(){
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(playerName);
        out.writeInt(playerScore);
    }

    public static final Parcelable.Creator<PlayerInfo> CREATOR = new Parcelable.Creator<PlayerInfo>() {
        public PlayerInfo createFromParcel(Parcel in) {
            return new PlayerInfo();
        }

        public PlayerInfo[] newArray(int size) {
            return new PlayerInfo[size];
        }
    };

    public JSONObject getJSONObject() {
        JSONObject obj = new JSONObject();
        try {
            obj.put("Name", playerName);
            obj.put("Score", playerScore);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
}
