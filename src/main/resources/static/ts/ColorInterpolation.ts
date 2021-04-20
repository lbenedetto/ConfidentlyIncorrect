// Converts a #ffffff hex string into an [r,g,b] array
function hexToRgb(hex: string) {
    const result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? [
        parseInt(result[1], 16),
        parseInt(result[2], 16),
        parseInt(result[3], 16)
    ] : null;
}

// Converts an [r,g,b] array into a #ffffff hex string
function rgbToHex(rgb: Array<number>) {
    return "#" + ((1 << 24) + (rgb[0] << 16) + (rgb[1] << 8) + rgb[2]).toString(16).slice(1);
}

// Interpolates between two colors
export function interpolateColor(fromColor: string, toColor: string, factor: number) {
    let color1 = hexToRgb(fromColor);
    let color2 = hexToRgb(toColor);
    let result = color1.slice();
    for (let i = 0; i < 3; i++) {
        result[i] = Math.round(result[i] + factor * (color2[i] - color1[i]));
    }
    return rgbToHex(result);
}