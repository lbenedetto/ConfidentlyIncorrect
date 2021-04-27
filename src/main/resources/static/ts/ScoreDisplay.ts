import {interpolateColor} from "./ColorInterpolation";

export function getColor(score: number): string {
    switch (true) {
        case score < -3:
            return interpolateColor("#d9534f", "#f0ad4e", (1/55) * (Math.round(score) + 58));
        case score < 0:
            return interpolateColor("#f0ad4e", "#f7f7f7", (1/3) * (Math.round(score) + 3));
        default:
            return interpolateColor("#f7f7f7", "#5cb85c", (1/10) * (Math.round(score)));
    }
}

export function getExplanationText(score: number): string {
    switch (true) {
        case score < -50:
            return "Catastrophically Atrocious!!"
        case score < -40:
            return "Catastrophic!"
        case score < -30:
            return "Terrible!"
        case score < -20:
            return "Awful!"
        case score < -10:
            return "Bad!"
        case score < -5:
            return "Unacceptable"
        case score < 0:
            return "Poor"
        case score < 1:
            return "Meh"
        case score < 2:
            return "Acceptable"
        case score < 3:
            return "Okay";
        case score < 4:
            return "Nice";
        case score < 5:
            return "Solid!";
        case score < 6:
            return "Admirable!";
        case score < 7:
            return "Good!";
        case score < 8:
            return "Exceptional!";
        case score < 9:
            return "Terrific!";
        case score < 10:
            return "Literally Unbelievable!";
        default:
            return "Killtacular!!";
    }
}

export function getIcon(score: number) : string {
    switch (true) {
        case score > 0:
            return "trending_up";
        case score == 0:
            return "trending_flat";
        case score < 0:
            return "trending_down";
    }
}