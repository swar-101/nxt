import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        primary: { main: '#000000' },
        secondary: { main: '#ffffff' },
    },
    typography: {
        fontFamily: 'Helvetica, Arial, sans-serif',
    },
});

export default theme;