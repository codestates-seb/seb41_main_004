import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
:root {
   /*Color*/
    --point-color : #BB2649;
    --hover-color : #A1203E;
    --font-color : #222222;
    --sub-font-color : #777777;
    --light-font-color : #AAAAAA;
    --border-color : #D9D9D9;
    --background-color : #F1F1F1;
    --white-color : #ffffff;
    --green-color : #18A52E;

    /* FontSize */
    --title-font : 2.2rem;
    --big-font : 1.6rem;
    --main-font : 1.4rem;
    --small-font : 1rem;
    --caption-font : 1.2rem;

    /* FontWeight*/
    --regular-weight: 400;
    --bold-weight: 700;
}
 * {
    box-sizing : border-box;
 }
 html {
    font-size : 62.5%;
 }
 body {
    margin : 0;
    font-size:var(--main-font);
    font-family: 'Noto Sans KR', sans-serif;
    display:flex;
    flex-direction:row;
    justify-content:center;
    align-items:center;
    background-color:#fafafa;
 }
 ul,ol {
    list-style : none;
 }
 a {
    text-decoration : none;
 }
 a, ul,ol, p, h1, h2, h3, h4, h5 {
    margin : 0;
    padding : 0;
    color : var(--font-color);
 }
#root {
   width:100%;
   max-width:50rem;
   background-color: var(--white-color);
}
input {
   width:100%;
   border-radius:5px;
   border:1px solid var(--border-color);
   padding: 1.5rem;
   height: 4.5rem;
}
input:focus {
   outline:none;
   border:1px solid var(--point-color);
}
`;
export default GlobalStyle;
