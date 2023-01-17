import { createSlice } from "@reduxjs/toolkit";

const initialStateValue = {
  bannerImg: null,
  categorySmallId: 0,
  clubName: "",
  clubInfo: "",
  memberLimit: "",
  meetingDate: "",
  meetingTime: "",
  fee: 0,
  genderRestriction: "",
  birthYearMin: "",
  birthYearMax: "",
  genderSelected: "",
  isOnline: "",
  location: "",
  joinQustion: "",
};

export const azitInfoSlice = createSlice({
  name: "azitInfo",
  initialState: initialStateValue,
  reducers: {
    azitInfo: (state, action) => {
      state = action.payload;
    },
    azitCategory: (state, action) => {
      if (state === 1) {
        return state === "전시";
      }
    },
  },
});

export const { azitInfo } = azitInfoSlice.actions;
export default azitInfoSlice.reducer;
