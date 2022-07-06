import { render, waitFor, screen, getByTestId } from "@testing-library/react";
import axios from "axios";
import { UserDetailsClass } from "../components/UserDetails";
import UserService from "../service/LoginService"

jest.mock("axios");

test("Product List", async () => {
    const user = {
        userName: "ng2482",
        fullName: "Manindra",
        email: "ng2482@srmist.edu.in",
        gender: "Male",
        dob: "2001-06-25",
        role: "user",
        mobile_no: 9515962633,
        password: "$2a$10$MyiC.OlXhMRCjARePBjDxOgZ4NYfle84OHQuGc1/TH7deL9i55SYi",
        address: {
            house_no: "89/2-2-11-3",
            street_name: "Majestic function hall line",
            colony_name: "Balaji Nagar",
            city: "Kurnool",
            state: "Andhra Pradesh",
            pincode: 518006
        }
    }

    axios.get.mockResolvedValue({ data: user });
    render(<UserDetailsClass />);

    const userDetails = waitFor(() => screen.findAllByTestId
        ("user"));
    expect((await userDetails)).toHaveLength(1);
});