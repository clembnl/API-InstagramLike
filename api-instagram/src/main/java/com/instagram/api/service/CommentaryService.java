package com.instagram.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.instagram.api.dto.commentary.CommentaryDto;
import com.instagram.api.model.Commentary;
import com.instagram.api.model.Post;
import com.instagram.api.model.User;
import com.instagram.api.repository.CommentaryRepository;

@Service
public class CommentaryService {
	
	@Autowired
	private CommentaryRepository commentaryRepository;
	
	public List<CommentaryDto> listCommentarysByPost(Post post) {
		List<Commentary> commentaryList = commentaryRepository.findAllByPost(post);
        List<CommentaryDto> commentarys = new ArrayList<>();
        for (Commentary commentary:commentaryList){
            CommentaryDto commentaryDto = getDtoFromCommentary(commentary);
            commentarys.add(commentaryDto);
        }
        return commentarys;
	}
	
	public void deleteCommentary(final Integer id, User user) {
		commentaryRepository.deleteById(id);
	}
	
    public void addCommentary(CommentaryDto commentaryDto, User user, Post post) {
        Commentary commentary = getCommentaryFromDto(commentaryDto, user, post);
        commentaryRepository.save(commentary);
    }
    
    public void updateCommentary(CommentaryDto comDto, User user, Post post){
        Commentary com = commentaryRepository.findById(comDto.getId()).get();
        com.setCommentary(comDto.getCommentary());
        commentaryRepository.save(com);
    }
	
    public static CommentaryDto getDtoFromCommentary(Commentary commentary) {
        CommentaryDto commentaryDto = new CommentaryDto(commentary);
        return commentaryDto;
    }
    
    public static Commentary getCommentaryFromDto(CommentaryDto commentaryDto, User user, Post post) {
        Commentary commentary = new Commentary(commentaryDto, user, post);
        return commentary;
    }

}
