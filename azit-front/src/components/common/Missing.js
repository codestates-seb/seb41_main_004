import { Link } from "react-router-dom"
import styled from "styled-components"
import Header from "./Header"

const MissingWrap = styled.section`
padding: 5.5rem 2rem;
display: flex;
flex-direction: column;
justify-content: center;
align-items: center;
height: 100vh;
>h2 {
    color:var(--point-color);
    margin-bottom: 2rem;
    font-size:var(--title-font);
}
>a {
    font-size:var(--caption-font);
    color:var(--sub-font-color);
}
`
const Missing = () => {
    return (
        <MissingWrap>
            <Header />
            <h2>Page not Found</h2>
            <p>We're sorry, we couldn't find the page you requested.</p>
            <Link to="/">Link to Home</Link>
        </MissingWrap>
    )
}

export default Missing