package com.example.iot.repository;

import com.example.iot.domain.TemplateChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface TemplateChannelRepository extends JpaRepository<TemplateChannel, Integer> {
    TemplateChannel getById(int id);

    ArrayList<TemplateChannel> getAllByChannelType(int channelType);

    ArrayList<TemplateChannel> getAllByTemplateId(int templateId);

}
