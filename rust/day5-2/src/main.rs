use std::fs::File;
use std::io::{ self, BufRead, BufReader };
use std::collections::VecDeque;
use regex::Regex;

fn read_lines(filename: String) -> io::Lines<BufReader<File>> {
    let file = File::open(filename).unwrap();
    return io::BufReader::new(file).lines();
}

fn main() {
//    let lines = read_lines("./testdata/day5.txt".to_string());
    let lines = read_lines("../../inputs/day5.txt".to_string());

    let mut columns = Vec::new();
    for _n in 0..9 {
        columns.push(VecDeque::new());
    }

    let mut move_queue = VecDeque::new();

    let mut read_columns = true;
    let find_movement = Regex::new(r"move (\d+) from (\d+) to (\d+)").unwrap();

    for line in lines {
        if let Ok(line_unwrapped) = line {
            if line_unwrapped.trim().is_empty() {
                continue;
            }

            if read_columns {
                let len = line_unwrapped.len();
                for n in 0..9 {
                    let pos = n * 4 + 1;
                    if pos >= len {
                        continue;
                    }
                    let outchar = line_unwrapped.chars().nth(pos).unwrap();
                    if outchar.eq(&'1') {
                        read_columns = false;
                        break;
                    }
                    if !outchar.eq(&' ') {
                        columns[n].push_back(outchar);
                    }
                }
            } else {
                for cap in find_movement.captures_iter(&line_unwrapped) {
                    let moves = cap[1].parse::<i32>().unwrap();
                    let from = cap[2].parse::<i32>().unwrap();
                    let to = cap[3].parse::<i32>().unwrap();
                    for _n in 0..moves {
                        let val_wrapped = columns[(from - 1) as usize].pop_front();
                        if let Some(val) = val_wrapped {
                            move_queue.push_front(val);
                        }
                    }
                    for _n in 0..moves {
                        let val_wrapped = move_queue.pop_front();
                        if let Some(val) = val_wrapped {
                            columns[(to - 1) as usize].push_front(val);
                        }
                    }
                }
            }
        }
    }
    for n in 0..9 {
        if let Some(val) = columns[n].pop_front() {
            print!("{}", val);
        }
    }
    println!("");
}