
export function renderIf(condition, elementIfTrue, elementIfFalse = false) {
    return condition ? elementIfTrue : elementIfFalse;
}
