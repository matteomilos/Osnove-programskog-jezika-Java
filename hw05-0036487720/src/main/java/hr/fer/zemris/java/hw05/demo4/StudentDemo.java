package hr.fer.zemris.java.hw05.demo4;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get("./studenti.txt"), StandardCharsets.UTF_8);
		List<StudentRecord> records = convert(lines);

		long broj = vratiBodovaViseOd25(records);

		long broj5 = vratiBrojOdlikasa(records);

		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);

		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);

		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);

		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);

		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);

		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);

	}

	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {

		return records.stream().collect(Collectors.partitioningBy((s1) -> s1.getFinalGrade() > 1));
	}

	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(
				Collectors.toMap(StudentRecord::getFinalGrade, s -> 1, (oldCount, newCount) -> oldCount + newCount));
	}

	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream().collect(Collectors.groupingBy(StudentRecord::getFinalGrade));
	}

	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 1).map(s -> s.getJmbag()).sorted()
				.collect(Collectors.toList());
	}

	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5)
				.sorted((s1, s2) -> -Double.compare(s1.getSumOfAllPoints(), s2.getSumOfAllPoints()))
				.collect(Collectors.toList());
	}

	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).collect(Collectors.toList());
	}

	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getFinalGrade() == 5).count();
	}

	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream().filter(s -> s.getSumOfAllPoints() > 25).count();
	}

	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for (String line : lines) {
			String[] splitted = line.split("\t");
			if (splitted.length == 7) {
				records.add(new StudentRecord(splitted));
			}
		}
		return records;
	}

}
