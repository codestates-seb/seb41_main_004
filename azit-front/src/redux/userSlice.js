import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  userInfo: {}
};

export const userInfoSlice = createSlice({
  name: 'userInfoSlice',
  initialState,
  reducers: {
    getUserInfo: (state, action) => {state.userInfo = action.payload},
  }
})