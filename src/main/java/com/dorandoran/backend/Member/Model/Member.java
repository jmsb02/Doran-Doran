package com.dorandoran.backend.Member.Model;

import com.dorandoran.backend.Club.Model.Club;
import com.dorandoran.backend.Comment.Model.Comment;
import com.dorandoran.backend.Post.Model.Post;
import com.dorandoran.backend.Reply.Model.Reply;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class Member {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Post> posts = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Club> clubs = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Reply> replies = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "member_id")
    private List<Reply> files = new ArrayList<>();

}
