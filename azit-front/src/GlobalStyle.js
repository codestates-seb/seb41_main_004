import { createGlobalStyle } from "styled-components";

const GlobalStyle = createGlobalStyle`
:root {
    --point-color : #BB2649;
    --hover-color : #A1203E;
    --font-color : #222222;
    --sub-font-color : #777777;
    --light-font-color : #AAAAAA;
    --border-color : #D9D9D9;
    --background-color : #F1F1F1;
    --white-color : #ffffff;
    --green-color : #18A52E;
    --title-font : 2rem;
}
 * {
    box-sizing : border-box;
 }
 html {
    font-size : 62.5%;
 }
 body {
    margin : 0;
    font-size:1.2rem;
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
 }

`;
export default GlobalStyle;
