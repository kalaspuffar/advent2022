use std::fs::File;
use std::io::{ self, BufRead, BufReader };

fn read_lines(filename: String) -> io::Lines<BufReader<File>> {
    let file = File::open(filename).unwrap();
    return io::BufReader::new(file).lines();
}

fn main() {
    let lines = read_lines("../../inputs/day2.txt".to_string());

    let rock = 1;
    let paper = 2;
    let scissor = 3;

    let win = 6;
    let even = 3;
    let loss = 0;


    let mut score: i32 = 0;
    for line in lines {
        if let Ok(line_unwrapped) = line {

            let split = line_unwrapped.split(" ");
            let vec: Vec<&str> = split.collect();

            if vec[0].eq(&String::from('A')) && vec[1].eq(&String::from('X')) {
                score += even + rock;
            } else
            if vec[0].eq(&String::from('A')) && vec[1].eq(&String::from('Y')) {
                score += win + paper;
            } else
            if vec[0].eq(&String::from('A')) && vec[1].eq(&String::from('Z')) {
                score += loss + scissor;
            } else
            if vec[0].eq(&String::from('B')) && vec[1].eq(&String::from('X')) {
                score += loss + rock;
            } else
            if vec[0].eq(&String::from('B')) && vec[1].eq(&String::from('Y')) {
                score += even + paper;
            } else
            if vec[0].eq(&String::from('B')) && vec[1].eq(&String::from('Z')) {
                score += win + scissor;
            } else
            if vec[0].eq(&String::from('C')) && vec[1].eq(&String::from('X')) {
                score += win + rock;
            } else
            if vec[0].eq(&String::from('C')) && vec[1].eq(&String::from('Y')) {
                score += loss + paper;
            } else
            if vec[0].eq(&String::from('C')) && vec[1].eq(&String::from('Z')) {
                score += even + scissor;
            }

        }
    }

    println!("{}", score);
}