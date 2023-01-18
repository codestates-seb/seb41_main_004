import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  name: "테스트용도",
};

export const userProfileSlice = createSlice({
  name: "userProfileSlice",
  initialState,
  reducers: {
    getUserProfile: (state, action) => {
      state.name = action.payload;
    },
  },
});
