import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Level5 {
    static Maze m;
    static Set<Position> visited;
    static Set<List<Position>> paths;

    public static void main(String[] args) throws IOException {
        for (int j = 1; j < 6; j++) {
            Path p = Path.of("data/level5_"+j+".in");
            String s = Files.readAllLines(p).stream().collect(Collectors.joining(System.lineSeparator())).replaceAll("-", "");
            String[] parts = s.split(System.lineSeparator()+System.lineSeparator());
            for (int i = 1; i < parts.length; i++) {
                visited = new HashSet<>();
                paths = new HashSet<>();
                m = new Maze(parts[i].split(System.lineSeparator()));
                getAllPaths(m.wasp);
                System.out.println(paths.size());
            }
        }

    }

    private static void getAllPaths(Position p) {
        visited.add(p);
        if(m.isBorder(p)) {
            paths.add(new ArrayList<>(visited));
            visited.remove(p);
            return;
        }
        List<Position> around = getCellsAround(p);
        for (Position position : around) {
            if (!visited.contains(position)) {
                getAllPaths(position);
            }
        }
        visited.remove(p);
    }


    public static List<Position> getCellsAround(Position p) {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position(p.x, p.y-1));
        positions.add(new Position(p.x, p.y+1));
        positions.add(new Position(p.x+1, p.y));
        positions.add(new Position(p.x-1, p.y));
        if(p.y % 2 == 0) {
            positions.add(new Position(p.x-1, p.y-1));
            positions.add(new Position(p.x-1, p.y+1));
        } else {
            positions.add(new Position(p.x+1, p.y-1));
            positions.add(new Position(p.x+1, p.y+1));
        }
        positions.removeIf(e -> !m.isIn(e) || !m.arr[e.y][e.x]);
        return positions;
    }


    static class Maze {
        boolean[][] arr;
        Position wasp;

        public Maze(String[] array) {
            arr = new boolean[array.length-1][array[1].length()];
            for (int i = 1; i < array.length; i++) {
                for (int j = 0; j < array[i].length(); j++) {
                    if(array[i].charAt(j) == 'W') {
                        wasp = new Position(j, i-1);
                        arr[i-1][j] = true;
                    } else if (array[i].charAt(j) == 'O'){
                        arr[i-1][j] = true;
                    } else {
                        arr[i-1][j] = false;
                    }
                }
            }
        }

        public boolean isIn(Position p) {
            return p.x >= 0 && p.x <= arr[0].length-1 && p.y >= 0 && p.y <= arr.length-1;
        }

        public boolean isBorder(Position p) {
            return p.x == 0 || p.x == arr[0].length -1 || p.y == 0 || p.y == arr.length-1;
        }
    }


    public static class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position() {
        }

        @Override
        public String toString() {
            return "(" + x + "|" + y + ")";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}