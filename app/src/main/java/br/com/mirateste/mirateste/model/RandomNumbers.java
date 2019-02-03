package br.com.mirateste.mirateste.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomNumbers implements Parcelable {

    private int randomNum;
    private List<Integer> generateNumbers;
    private String verifyTxt;

    public RandomNumbers(int randomNum, List<Integer> generateNumbers, String verifyTxt){
        this.randomNum = randomNum;
        this.generateNumbers = generateNumbers;
        this.verifyTxt = verifyTxt;
    }

    protected RandomNumbers(Parcel in) {
        randomNum = in.readInt();
        verifyTxt = in.readString();
    }

    public static final Creator<RandomNumbers> CREATOR = new Creator<RandomNumbers>() {
        @Override
        public RandomNumbers createFromParcel(Parcel in) {
            return new RandomNumbers(in);
        }

        @Override
        public RandomNumbers[] newArray(int size) {
            return new RandomNumbers[size];
        }
    };

    public int getRandomNum() {
        return randomNum;
    }

    public void setRandomNum(int randomNum) {
        this.randomNum = randomNum;
    }

    public List<Integer> getGenerateNumbers() {
        return generateNumbers;
    }

    public void setGenerateNumbers(List<Integer> generateNumbers) {
        this.generateNumbers = generateNumbers;
    }

    public String getVerifyTxt() {
        return verifyTxt;
    }

    public void setVerifyTxt(String verifyTxt) {
        this.verifyTxt = verifyTxt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(randomNum);
        dest.writeString(verifyTxt);
    }
}
