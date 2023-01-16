import { configureStore } from "@reduxjs/toolkit";
import { loginStatusSlice } from "./loginSlice";
import { userProfileSlice } from "./userProfileSlice";
import { userInfoSlice } from "./userSlice";

export const store = configureStore({
  reducer: {
    loginStatus: loginStatusSlice.reducer,
    userInfo: userInfoSlice.reducer,
    userprofile: userProfileSlice.reducer,
  },
});
