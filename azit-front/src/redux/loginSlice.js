import { createSlice } from "@reduxjs/toolkit";

const initialState = {
  isLogin: false
};

export const loginStatusSlice = createSlice({
  name: 'loginStatusSlice',
  initialState,
  reducers: {
    login: (state, action) => {state.isLogin = true},
    logout: (state, action) => {state.isLogin = false},
  }
})