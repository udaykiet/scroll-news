package com.scroll.app.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.scroll.app.models.News;
import com.scroll.app.services.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

	private final NewsService newsService;


	@Override
	public void run(String... args) throws Exception {

		log.info("Loading initial sample data...");

		// Create sample news
		News news1 = new News();
		news1.setTitle("OpenAI Launches New GPT-5 Model");
		news1.setSummary("OpenAI has released GPT-5, their most advanced language model yet. The new model shows significant improvements in reasoning and coding capabilities. It can handle longer contexts and provides more accurate responses across various domains.");
		news1.setContent("Full content about GPT-5 launch...");
		news1.setSource("TechCrunch");
		news1.setCategory(News.Category.TECHNOLOGY);
		news1.setImageUrl("https://via.placeholder.com/600x400");
		news1.setSource("https://techcrunch.com/gpt5");
		news1.setPublishedAt(LocalDateTime.now().minusHours(2));

		News news2 = new News();
		news2.setTitle("India Wins Cricket World Cup 2024");
		news2.setSummary("Team India defeated Australia by 6 wickets in the final to win the Cricket World Cup 2024. Virat Kohli scored a brilliant century. The match was held at the Narendra Modi Stadium in Ahmedabad.");
		news2.setContent("Full content about cricket match...");
		news2.setSource("ESPN Cricinfo");
		news2.setCategory(News.Category.SPORTS);
		news2.setImageUrl("https://via.placeholder.com/600x400");
		news2.setUrl("https://espncricinfo.com/worldcup");
		news2.setPublishedAt(LocalDateTime.now().minusHours(5));

		News news3 = new News();
		news3.setTitle("Stock Market Hits Record High");
		news3.setSummary("Sensex crossed 75,000 mark for the first time in history. Strong corporate earnings and positive global cues drove the rally. Banking and IT stocks led the gains with impressive performance.");
		news3.setContent("Full content about stock market...");
		news3.setSource("Economic Times");
		news3.setCategory(News.Category.BUSINESS);
		news3.setImageUrl("https://via.placeholder.com/600x400");
		news3.setUrl("https://economictimes.com/sensex");
		news3.setPublishedAt(LocalDateTime.now().minusHours(1));

		// Save sample news
		newsService.saveNews(news1);
		newsService.saveNews(news2);
		newsService.saveNews(news3);

		log.info("Sample data loaded successfully!");
	}
}
