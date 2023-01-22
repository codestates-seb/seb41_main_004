import { configureStore } from "@reduxjs/toolkit";
import { loginStatusSlice } from "./loginSlice";
import { userProfileSlice } from "./userProfileSlice";
import { userInfoSlice } from "./userSlice";

import { combineReducers } from "@reduxjs/toolkit";
import { persistReducer } from "redux-persist";
import storage from "redux-persist/lib/storage"; // local Storage
import thunk from 'redux-thunk';


const reducers = combineReducers({
    loginStatus: loginStatusSlice.reducer,
    userInfo: userInfoSlice.reducer,
    userProfile: userProfileSlice.reducer,
});

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ["loginStatus"]
};

const persistedReducer = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistedReducer,
  devTools: process.env.NODE_ENV !== 'production',
  middleware: [thunk],
});

export default store;
// export const store = configureStore({
//   reducer: {
//     loginStatus: loginStatusSlice.reducer,
//     userInfo: userInfoSlice.reducer,
//     userProfile: userProfileSlice.reducer,
//   },
// });
