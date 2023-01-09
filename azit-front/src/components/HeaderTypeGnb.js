import styled from "styled-components"

const HeaderWrap = styled.header`
    height: 5.5rem;
    display: flex;
    flex-direction: row;
    align-items: center;
    padding:0 2rem;
    >h2 {
        font-size:var(--title-font);
        font-weight: var(--bold-weight);
    }
`
const HeaderTypeGnb = ({title}) => {
    return (
        <HeaderWrap>
            <h2>{title}</h2>
        </HeaderWrap>
    )
}

export default HeaderTypeGnb;