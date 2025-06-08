
/** TODO : Important steps
 * Does:
 * getByText
 * render()
 */

import CustomButton, { ButtonState } from "./CustomButton"
import { fireEvent, render } from "@testing-library/react" // Isn't found, must manually be imported
import "@testing-library/jest-dom" // Isn't found, must manually be imported

// DisplayName() JUNIT == Collection of Tests
describe("Easy Examples", () => {
  // Test #1
  test("2 + 2 = 4", () => {
    expect(2 + 2).toBe(4)
  })

  // Test #2
  test("is 2+4 greater than 0", () => {
    expect(2 + 4).toBeGreaterThan(0)
  })

  // Test #3
  test("2-2 equals 0", () => {
    expect(2 - 2).toBe(0)
  })
})

// TODO - Custom Button component + State + Classname
describe("Custom Button Component States", () => {
  // Idle
  test("Check if Button Component starts with Idle", () => {
    const buttonText: string = "signup"
    const { getByText } = render(<CustomButton buttonText={buttonText} />) // TODO - Check if button component start with idle
    const customButtonComponent = getByText(buttonText)

    expect(customButtonComponent).toHaveClass(ButtonState.idle)
  })

  // Hover
  test("Check if Button Component on hover state change", () => {
    const buttonText: string = "signup"
    const { getByText } = render(<CustomButton buttonText={buttonText} />) // TODO - Check if button component start with idle
    const customButtonComponent = getByText(buttonText)

    expect(customButtonComponent).toHaveClass(ButtonState.idle)
    fireEvent.mouseEnter(customButtonComponent)
    expect(customButtonComponent).toHaveClass(ButtonState.hover)
  })

  // Clicked
  test("Custom Button Component on click state change", () => {
    const buttonText: string = "signup"
    const { getByText } = render(<CustomButton buttonText={buttonText} />) // TODO - Check if button component start with idle
    const customButtonComponent = getByText(buttonText)

    expect(customButtonComponent).toHaveClass(ButtonState.idle)
    fireEvent.click(customButtonComponent)
    expect(customButtonComponent).toHaveClass(ButtonState.clicked)
  })
})
